package com.webee.challange.api;

import com.webee.challange.repository.models.Device;
import com.webee.challange.services.DeviceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceServices deviceServices;

    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public void addDevice(@RequestBody Device jsonDevice) {
        deviceServices.addDevice(jsonDevice);
    }

    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public Iterable<Device> getAllDevice() {
        return deviceServices.getAllDevice();
    }

    @RequestMapping(value = "/device/macAddress/{macAddress}", method = RequestMethod.GET)
    public Optional<Device> getDeviceByMacAddress(@PathVariable String macAddress) {
        return deviceServices.getDeviceByMacAddress(macAddress);
    }

    @RequestMapping(value = "/device/id/{id}", method = RequestMethod.GET)
    public Optional<Device> getDeviceById(@PathVariable String id) {
        return deviceServices.getDeviceById(id);
    }

    @RequestMapping(value = "/device/{id}", method = RequestMethod.DELETE)
    public void deleteDeviceById(@PathVariable String id) {
        deviceServices.deleteDeviceById(id);
    }

}
