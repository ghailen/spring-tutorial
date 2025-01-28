package com.ghailene.billing_job.processors;

import com.ghailene.billing_job.records.BillingData;
import com.ghailene.billing_job.records.ReportingData;
import com.ghailene.billing_job.services.PricingService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class BillingDataProcessor implements ItemProcessor<BillingData, ReportingData> {

    private final PricingService pricingService;

    public BillingDataProcessor(PricingService pricingService) {
        this.pricingService = pricingService;
    }
    @Value("${spring.cellular.pricing.data:0.01}")
    private float dataPricing;

    @Value("${spring.cellular.pricing.call:0.5}")
    private float callPricing;

    @Value("${spring.cellular.pricing.sms:0.1}")
    private float smsPricing;

    @Value("${spring.cellular.spending.threshold:150}")
    private float spendingThreshold;

    /** without errors tests
      @Override
    public ReportingData process(BillingData item) {
        double billingTotal = item.dataUsage() * dataPricing + item.callDuration() * callPricing + item.smsCount() * smsPricing;
        if (billingTotal < spendingThreshold) {
            return null;
        }
        return new ReportingData(item, billingTotal);
    }*/

    @Override
    public ReportingData process(BillingData item) {
        double billingTotal =
                item.dataUsage() * pricingService.getDataPricing() +
                        item.callDuration() * pricingService.getCallPricing() +
                        item.smsCount() * pricingService.getSmsPricing();
        if (billingTotal < spendingThreshold) {
            return null;
        }
        return new ReportingData(item, billingTotal);
    }

}
