package com.practice.config;

import com.practice.Entity.Address;
import com.practice.repo.AddressRepo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@Configuration
public class BatchConfig {

	
	private final JobRepository jobRepository;
    private final AddressRepo addRepo;
    private final JpaTransactionManager transactionManager;
    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext; // Inject ApplicationContext instead of job


    public BatchConfig(JobRepository jobRepository, 
    		AddressRepo addRepo, 
                       JpaTransactionManager transactionManager,
                       JobLauncher jobLauncher,
                       ApplicationContext applicationContext) {
        this.jobRepository = jobRepository;
        this.addRepo = addRepo;
        this.jobLauncher=jobLauncher;
        this.applicationContext=applicationContext;
        this.transactionManager = transactionManager;
    }
	
    @Bean
    public FlatFileItemReader<Address> reader(){
    	FlatFileItemReader<Address> reader = new FlatFileItemReader<Address>();
    	reader.setResource(new ClassPathResource("batch_data.csv"));
    	reader.setLinesToSkip(1);
    	

        DefaultLineMapper<Address> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        
        tokenizer.setNames("city","state","pincode");
        
        BeanWrapperFieldSetMapper<Address> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Address.class);
        
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        
        reader.setLineMapper(lineMapper);
        
        return reader;
    }
    
    @Bean
    public ItemProcessor<Address, Address> processor() {
        return address -> {
            if (address.getCity() == null || address.getState() == null || address.getPincode() == null) {
                throw new IllegalArgumentException("Address fields cannot be null");
            }

            Optional<Address> exists = addRepo.findByCityAndStateAndPincode(
                address.getCity(), 
                address.getState(), 
                address.getPincode()
            );

            if (exists.isPresent()) {
                Address updatedAddress = exists.get();
                updatedAddress.setCity(address.getCity().toUpperCase()); // Example of an update
                return updatedAddress;
            }

            address.setId(null);
            address.setCity(address.getCity().toUpperCase());

            return address;
        };
    }

    
    @Bean
    public ItemWriter<Address> writer(){
        return addresses -> {
            for (Address address : addresses) {
                Optional<Address> existingAddress = addRepo.findByCityAndStateAndPincode(
                        address.getCity(),
                        address.getState(),
                        address.getPincode()
                );
                if (existingAddress.isPresent()) {
                    Address updatedAddress = existingAddress.get();
                    if(!updatedAddress.equals(address)){
                        updatedAddress.setCity(address.getCity());
                        updatedAddress.setPincode(address.getPincode());
                        updatedAddress.setState(address.getState());
                        addRepo.save(updatedAddress);  // Update existing record
                        System.out.println("Updated record: " + updatedAddress);
                    }
                    else {
                        System.out.println("skipping this"+existingAddress);
                    }
                }
                 else {
                    addRepo.save(address); // Insert new record
                    System.out.println("Inserted new record: " + address);
                }
            }
        };
    }
    
    @Bean
    public Step step() {
    	return new StepBuilder("step1",jobRepository)
    			.<Address,Address>chunk(10,transactionManager)
    			.reader(reader())
    			.processor(processor())
    			.writer(writer())
                .allowStartIfComplete(true) // starts job even the job is already processed.use case new records
                // in the same file to be updated..
                .build();
    }
    
    @Bean
    public Job importAddressJob() {
    	return new JobBuilder("importAddressJob",jobRepository)
    			.start(step())
    			.build();
    }
    
    //without any scheduler when app runs this will be launch the job..
    @Scheduled(fixedRate = 300000) // 5 minutes (300000 ms)
    public void runJob() throws Exception {
        Job importAddressJob = applicationContext.getBean("importAddressJob", Job.class); // Get job from context
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(importAddressJob, jobParameters);
    }
}

