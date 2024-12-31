package com.ttisv.springbootwildfly.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttisv.service.system.TblBookingService;
import com.ttisv.service.system.VnPayService;

@RestController
@RequestMapping("/api/payment")
public class VnPayController extends BaseController  {

    @Autowired
    private VnPayService vnpayService; 
    @Autowired
 TblBookingService bookingService;

    @GetMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestParam Long bookingId, @RequestParam Double amount) {
        try {
            String paymentUrl = vnpayService.createPaymentUrl(bookingId, amount);
            if (paymentUrl == null) {
                throw new RuntimeException("Failed to generate payment URL");
            }
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment URL");
        }
    }

    
    @PostMapping("/vnpay-return")
    public ResponseEntity<String> vnpayReturn(@RequestBody Map<String, String> vnpParams) {
        vnpParams.forEach((key, value) -> System.out.println("Parameter Name - " + key + ", Value - " + value));
        
        // Lấy mã phản hồi và transaction reference từ VNPay
        String vnpResponseCode = vnpParams.get("vnp_ResponseCode");
        String txnRef = vnpParams.get("vnp_TxnRef");

        if (txnRef == null || txnRef.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid transaction reference (vnp_TxnRef)");
        }

        Long bookingId;
        try {
            bookingId = Long.parseLong(txnRef);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid booking ID format");
        }

        if ("00".equals(vnpResponseCode)) {
           
            try {
                bookingService.updateStatusPayment(bookingId, "booked");
                return ResponseEntity.ok("Payment success and booking status updated");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment success but failed to update booking");
            }
        } else {
            // Giao dịch thất bại
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
        }
    }

}
