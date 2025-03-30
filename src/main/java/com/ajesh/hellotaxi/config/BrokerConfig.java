package com.ajesh.hellotaxi.config;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingCategory;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import com.ajesh.hellotaxi.strategy.AvailableStatusStrategy;
import com.ajesh.hellotaxi.strategy.BookingDispatchStrategy;
import com.ajesh.hellotaxi.strategy.DefaultStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class BrokerConfig {

    @Bean
    public BookingDispatchStrategy defaultStrategy() {
        return new DefaultStrategy();
    }

    @Bean
    public BookingDispatchStrategy availableStatusStrategy(TaxiRepository taxiRepository) {
        return new AvailableStatusStrategy(taxiRepository);
    }

    @Bean
    public BookingBroker bookingBroker(BookingDispatchStrategy defaultStrategy, BookingDispatchStrategy availableStatusStrategy) {
        Map<BookingCategory, BookingDispatchStrategy> strategyMap = Map.of(
                BookingCategory.NORMAL, defaultStrategy,
                BookingCategory.PREMIUM, availableStatusStrategy
        );
        return new BookingBroker(strategyMap);
    }
}
