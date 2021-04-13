package com.webee.challange.services;

import com.webee.challange.repository.models.Device;
import com.webee.challange.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DeviceServices {

    @Autowired
    private DeviceRepository deviceRepository;

    @ExceptionHandler
    public void addDevice(Device device) throws ErrorService {
        if (validateMacAddress(device.getMacAddress())) {
            try {
                if (device.getTimestamp() == null) {
                    device.setTimestamp(new Date());
                }
                if (validateTimestamp(device.getTimestamp())) {
                        deviceRepository.save(device);
                        log.info("The Device with mac address {} was added successfully", device.getMacAddress());
                } else {
                    throw new ErrorService("invalid timestamp");
                }
            } catch (DataIntegrityViolationException e) {
                log.error("Mac address {} already exists. Device was not added", device.getMacAddress());
                throw e;
            }
        } else {
            log.error("Invalid Mac address = {} - The device was not added", device.getMacAddress());
            throw new ErrorService("Invalid mac address");
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

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public void deleteDeviceById(Long id) throws Exception {
        log.info("Removing device with id {}", id);
        deviceRepository.deleteById(id);
    }

    private boolean validateMacAddress(String macAddress) {

        String regex = "^([A-Z]{2}[::])"
                + "{5}([A-Z]{2})|"
                + "([A-Z]{4}\\."
                + "[A-Z]{4}\\."
                + "[A-Z]{4})$";

        Pattern p = Pattern.compile(regex);

        if (macAddress == null) {
            return false;
        }

        Matcher m = p.matcher(macAddress);
        return m.matches();
    }

    private boolean validateTimestamp(Date timestamp) {
        Calendar limitDate = Calendar.getInstance();
        limitDate.set(2020,01,01,0,0,0);
        return (!limitDate.getTime().after(timestamp));
    }
}