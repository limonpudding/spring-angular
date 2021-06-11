package ru.dfsystems.spring.tutorial.security;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthMonitoring {

    private final Map<String, LoginAttempts> monitoring;

    public AuthMonitoring() {
        this.monitoring = new HashMap<>();
    }

    public boolean checkLoginAbility(String login) {
        boolean res = false;
        synchronized (this.monitoring) {
            Calendar valid = Calendar.getInstance();
            valid.roll(Calendar.MINUTE, 10);
            Date teenMinutesBeforeNow = valid.getTime();

            LoginAttempts attempts = monitoring.get(login);
            if (attempts != null && attempts.getCount() >= 3) {
                if (attempts.getLastAttemptDate().after(teenMinutesBeforeNow)) {
                    monitoring.remove(login);
                    res = true;
                }
            } else {
                res = true;
            }
        }
        return res;
    }

    public void addFailedLoginAttempt(String login) {
        synchronized (monitoring) {
            LoginAttempts attempts = monitoring.get(login);
            if (attempts != null) {
                attempts.increaseCount();
            } else {
                monitoring.put(login, new LoginAttempts());
            }
        }
    }

    public void clearUserAttempts(String login) {
        synchronized (monitoring) {
            monitoring.remove(login);
        }
    }

    private class LoginAttempts {
        private Integer count;
        private Date lastAttemptDate;

        public LoginAttempts() {
            count = 1;
            lastAttemptDate = new Date();
        }

        public Integer getCount() {
            return count;
        }

        private void setCount(Integer count) {
            this.count = count;
            this.lastAttemptDate = new Date();
        }

        public void increaseCount() {
            this.setCount(this.count + 1);
        }

        public Date getLastAttemptDate() {
            return lastAttemptDate;
        }

        public void setLastAttemptDate(Date lastAttemptDate) {
            this.lastAttemptDate = lastAttemptDate;
        }
    }
}
