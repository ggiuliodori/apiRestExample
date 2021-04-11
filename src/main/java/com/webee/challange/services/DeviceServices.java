package com.webee.challange.services;

import com.webee.challange.repository.models.Device;
import com.webee.challange.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DeviceServices {

    @Autowired
    private DeviceRepository deviceRepository;

    public void addDevice(Device device) {
        validateMacAddress(device.getMacAddress());
        deviceRepository.save(device);
        log.info("The Device with mac address {} was added successfully", device.getMacAddress());
    }

    public Iterable<Device> getAllDevice() {
        log.info("Getting all devices");
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceByMacAddress(String macAddress) {
        validateMacAddress(macAddress);
        log.info("Getting device with mac address {}", macAddress);
        return deviceRepository.findByMacAddress(macAddress);
    }

    public Optional<Device> getDeviceById(Long id) {
        validateId(id);
        log.info("Getting device with id {}", id);
        return deviceRepository.findById(id);
    }

    public void deleteDeviceById(Long id) {
        validateId(id);
        log.info("Removing device with id {}", id);
        deviceRepository.deleteById(id);
    }

    private void validateMacAddress(String macAddress) {
        //TODO: regular expression to validate macAddress
    }

    private void validateId(Long id) {
        //TODO: validate id
    }

}