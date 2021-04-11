package com.webee.challange.services;

import com.webee.challange.repository.Device;
import com.webee.challange.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DeviceServices {

    @Autowired
    private DeviceRepository deviceRepository;

    public void addDevice(Device device) {
        deviceRepository.save(device);
        log.info("The Device with mac address {} was added successfully", device.getMacAddress());
    }

    public Iterable<Device> getAllDevice() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceByMacAddress(String macAddress) {
        return deviceRepository.findByMacAddress(macAddress);
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public void deleteDeviceById(Long id) {
        deviceRepository.deleteById(id);
    }
}