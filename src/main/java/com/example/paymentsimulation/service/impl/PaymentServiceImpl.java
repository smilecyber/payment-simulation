package com.example.paymentsimulation.service.impl;

import com.example.paymentsimulation.model.*;
import com.example.paymentsimulation.repository.*;
import com.example.paymentsimulation.service.PaymentService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class PaymentServiceImpl implements PaymentService {

    private final MerchantRepository merchantRepository;
    private final PaymentRepository paymentRepository;
    private final BuyerRepository buyerRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final CartRepository cartRepository;

    public PaymentServiceImpl(MerchantRepository merchantRepository,
                              PaymentRepository paymentRepository,
                              BuyerRepository buyerRepository,
                              PaymentDetailRepository paymentDetailRepository,
                              CartRepository cartRepository) {
        this.merchantRepository = merchantRepository;
        this.paymentRepository = paymentRepository;
        this.buyerRepository = buyerRepository;
        this.paymentDetailRepository = paymentDetailRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void makePayment(Long merchantId, Long buyerId, Long cartId, BigDecimal amount) {
        Merchant merchant = merchantRepository.getById(merchantId);
        Buyer buyer = buyerRepository.getById(buyerId);
        Cart cart = cartRepository.getById(cartId);

        Payment payment = paymentRepository.save(Payment.builder()
                        .cart(cart).buyer(buyer).amount(amount)
                        .merchant(merchant).status(1).build());
        PaymentDetail paymentDetail = createPaymentDetail(payment);
        paymentDetailRepository.save(paymentDetail);
    }

    public PaymentDetail createPaymentDetail(Payment payment){
        Date payoutDate = calculatePayoutDate(payment.getCreatedDate()
                , payment.getMerchant().getPayoutDelayCount());

        BigDecimal commissionAmount = calculateProcessingFees(payment.getAmount(),
                payment.getMerchant().getCommissionRate(),
                payment.getMerchant().getTransactionFee());
        BigDecimal merchantPayoutAmount = payment.getAmount().subtract(commissionAmount);

        PaymentDetail paymentDetail = PaymentDetail.builder().
                payment(payment).payoutDate(payoutDate).
                commissionAmount(commissionAmount).
                merchantPayoutAmount(merchantPayoutAmount).build();
        return paymentDetail;

    }

    private Date calculatePayoutDate(Date paymentDate, int delayCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paymentDate);
        calendar.add(Calendar.DAY_OF_MONTH, delayCount);
        return calendar.getTime();
    }
    public BigDecimal calculateProcessingFees(BigDecimal amount,
                                              BigDecimal commissionRate, BigDecimal fee){
        return fee.add(amount.multiply(commissionRate).divide(new BigDecimal(100)));
    }

}
