package com.korit.mcdonaldkiosk.service.user;

import com.korit.mcdonaldkiosk.dto.request.ReqPointDto;
import com.korit.mcdonaldkiosk.entity.Point;
import com.korit.mcdonaldkiosk.repository.user.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    // 포인트 적립 및 차감 처리
    public void processPoint(ReqPointDto reqPointDto, int calcul) {
        System.out.println("Processing calcul value: " + calcul);
        System.out.println("Received phoneNumber: " + reqPointDto.getPhoneNumber());

        if (calcul != 0 && calcul != 1) {
            throw new IllegalArgumentException("Invalid calcul value: " + calcul);
        }

        Integer userId = pointRepository.findUserIdByPhoneNumber(reqPointDto.getPhoneNumber());
        System.out.println("Retrieved userId: " + userId);

        if (userId == null || userId == 0) {
            System.out.println("User not found, creating new user...");
            pointRepository.createNewUser(reqPointDto.getPhoneNumber());
            userId = pointRepository.findUserIdByPhoneNumber(reqPointDto.getPhoneNumber());

            if (userId == null || userId == 0) {
                throw new IllegalArgumentException("Failed to create user for phone number: " + reqPointDto.getPhoneNumber());
            }
        }

        System.out.println("Final userId: " + userId);

        if (calcul == 0) {
            System.out.println("Saving points...");
            pointRepository.save(reqPointDto.getPhoneNumber(), reqPointDto.getPoint(), calcul);
        } else {
            System.out.println("Updating points...");
            pointRepository.update(reqPointDto.getPhoneNumber(), reqPointDto.getPoint());
        }
    }

}

