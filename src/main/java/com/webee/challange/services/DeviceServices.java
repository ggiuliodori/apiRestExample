package com.webee.challange.services;

import com.webee.challange.repository.models.Device;
import com.webee.challange.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DeviceServices {

    @Autowired
    private DeviceRepository deviceRepository;

    @ExceptionHandler
    public void addDevice(Device device) {
        if (validateMacAddress(device.getMacAddress())) {
            try {
                deviceRepository.save(device);
                log.info("The Device with mac address {} was added successfully", device.getMacAddress());
            } catch (DataIntegrityViolationException e) {
                log.error("Mac address {} already exists. Device was not added");
            }
        } else {
            log.error("Invalid Mac address = {} - The device was not added", device.getMacAddress());
        }
    }

    public Iterable<Device> getAllDevice() {
        log.info("Getting all devices");
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceByMacAddress(String macAddress) {
        if (validateMacAddress(macAddress)) {
            log.info("Getting device with mac address {}", macAddress);
        } else {
            log.warn("Invalid Mac address = {}", macAddress);
        }
        return deviceRepository.findByMacAddress(macAddress);
    }

    public Optional<Device> getDeviceById(String id) {
        if (validateId(id)) {
            log.info("Getting device with id {}", id);
            return deviceRepository.findById(Long.valueOf(id));
        } else {
            log.warn("Invalid id {}", id);
            return null;
        }
    }

    public void deleteDeviceById(String id) {
        if (validateId(id)) {
            log.info("Removing device with id {}", id);
            deviceRepository.deleteById(Long.valueOf(id));
        } else {
            log.warn("Invalid id {}", id);
        }
    }

    private boolean validateMacAddress(String macAddress) {

        String regex = "^([0-9A-Za-z]{2}[::])"
                + "{5}([0-9A-Za-z]{2})|"
                + "([0-9a-zA-Z]{4}\\."
                + "[0-9a-zA-Z]{4}\\."
                + "[0-9a-zA-Z]{4})$";

        Pattern p = Pattern.compile(regex);

        if (macAddress == null) {
            return false;
        }

        Matcher m = p.matcher(macAddress);
        return m.matches();
    }

    private boolean validateId(String id) {

        String regex = "^[0-9]\\d*$";

        Pattern p = Pattern.compile(regex);

        try {
            if (id == null) {
                return false;
            }

            Matcher m = p.matcher(id);
            return m.matches();
        } catch (Exception e) {
            log.error("Validate Id error: {}", e.getMessage());
            return false;
        }
    }
}