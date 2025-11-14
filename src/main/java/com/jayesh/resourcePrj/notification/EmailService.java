package com.jayesh.resourcePrj.notification;

import com.jayesh.resourcePrj.dto.request.TrackReturnRequestDto;
import com.jayesh.resourcePrj.entities.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class EmailService {


    private final JavaMailSender mailSender;

    public void sendEmail(String to, LocalDate returnDate,String assetName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reminder !! From Re:Source");
        message.setText(
                "Dear Employee,\n\n" +
                        "This is a reminder regarding the asset that was issued to you.\n\n" +
                        "Asset Name: " + assetName + "\n" +
                        "Expected Return Date: " + returnDate + "\n\n" +
                        "Our records show that the asset has not been returned by the expected date.\n" +
                        "Please return the asset at the earliest or contact the administrator to request a date extension.\n\n" +
                        "Thank you,\n" +
                        "Re:Source Team"
        );
        mailSender.send(message);
    }

    public void sendEmailWhenAssigned(String to, Track track) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Asset Assignment Confirmation");

        message.setText(
                "Dear Employee,\n\n" +
                        "An asset has been successfully assigned to you.\n\n" +
                        "Asset Name: " + track.getAsset().getName() + "\n" +
                        "Issue Date: " + LocalDate.now() + "\n" +
                        "Expected Return Date: " + track.getExpectedReturnDate() + "\n\n" +
                        "Please ensure the safe use of the assigned asset.\n\n" +
                        "Regards,\n" +
                        "Re:Source Team"
        );
        mailSender.send(message);
    }


    public void sendEmailWhenReturned(String to, Track track) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Asset Return Confirmation");

        message.setText(
                "Dear Employee,\n\n" +
                        "Thank you for returning the assigned asset.\n\n" +
                        "Asset Name: " + track.getAsset().getName() + "\n" +
                        "Return Date: " + track.getReturnDate() + "\n\n" +
                        "The return has been successfully updated in the system.\n\n" +
                        "Regards,\n" +
                        "Re:Source Team"
        );
        mailSender.send(message);
    }


}
