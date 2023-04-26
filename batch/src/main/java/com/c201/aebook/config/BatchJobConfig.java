package com.c201.aebook.config;

import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.web.client.RestTemplate;

import com.c201.aebook.api.batch.AladinBatchItemReader;
import com.c201.aebook.api.batch.AladinBatchItemWriter;
import com.c201.aebook.api.book.persistence.entity.BookEntity;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

	private static final int CHUNK_SIZE = 1000;
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final AladinBatchItemReader aladinBatchItemReader;

	private final AladinBatchItemWriter aladinBatchItemWriter;

	@Autowired
	public BatchJobConfig(JobBuilderFactory jobBuilderFactory,
		StepBuilderFactory stepBuilderFactory, AladinBatchItemReader aladinBatchItemReader,
		AladinBatchItemWriter aladinBatchItemWriter) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.aladinBatchItemReader = aladinBatchItemReader;
		this.aladinBatchItemWriter = aladinBatchItemWriter;
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("batch job")
			.incrementer(new RunIdIncrementer())
			.start(step())
			.build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("batch step")
			.startLimit(5) //재시작 5번 가능
			.<BookEntity, BookEntity>chunk(CHUNK_SIZE)
			.reader(aladinBatchItemReader)
			.writer(aladinBatchItemWriter)
			.faultTolerant()
			.retryLimit(3) //재시도 3번 가능
			.retry(DataAccessException.class)
			.retry(ConnectionPoolTimeoutException.class)
			.build();
	}



}
