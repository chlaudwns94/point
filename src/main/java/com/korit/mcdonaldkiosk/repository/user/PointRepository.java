package com.korit.mcdonaldkiosk.repository.user;

import com.korit.mcdonaldkiosk.entity.Point;
import com.korit.mcdonaldkiosk.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepository {

    @Autowired
    private PointMapper pointMapper;

    // 포인트 차감 메서드
    public void update(String phoneNumber, int point) {
        int userId = findUserIdByPhoneNumber(phoneNumber);
        if (userId == 0) {
            throw new IllegalArgumentException("User not found for phone number: " + phoneNumber);
        }

        System.out.println("Updating points for userId: " + userId);

        Point updatePoint = new Point();
        updatePoint.setUserId(userId);
        updatePoint.setPoint(point);
        pointMapper.updatePoint(updatePoint);
    }
    // 핸드폰 번호로 유저 ID 조회
    public Integer findUserIdByPhoneNumber(String phoneNumber) {
        Integer userId = pointMapper.findUserIdByPhoneNumber(phoneNumber);
        return userId != null ? userId : 0; // null이면 0 반환
    }

    // 포인트 적립 메서드 수정
    public void save(String phoneNumber, int point, int calcul) {
        int userId = findUserIdByPhoneNumber(phoneNumber);
        if (userId == 0) {
            throw new IllegalArgumentException("User not found for phone number: " + phoneNumber);
        }

        Point newPoint = new Point();
        newPoint.setUserId(userId);
        newPoint.setCalcul(calcul);
        newPoint.setPoint(point);
        pointMapper.insertPoint(newPoint);
    }

    // 새로운 유저 생성 (user_tb에 저장)
    public void createNewUser(String phoneNumber) {
        pointMapper.createNewUser(phoneNumber);
    }
}
