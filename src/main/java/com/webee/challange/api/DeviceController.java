package com.webee.challange.api;

import com.webee.challange.repository.models.Device;
import com.webee.challange.services.DeviceServices;
import com.webee.challange.services.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceServices deviceServices;

    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public ResponseEntity<?> addDevice(@RequestBody Device jsonDevice) {
        try {
            deviceServices.addDevice(jsonDevice);
            log.info("status code: {}", HttpStatus.CREATED);
            return new ResponseEntity<>(jsonDevice, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse jsonResponse = new ErrorResponse();
            jsonResponse.setTimestamp(new Date());
            jsonResponse.setStatus(HttpStatus.CONFLICT);
            jsonResponse.setMessage(e.getMessage());
            jsonResponse.setCode(409);
            jsonResponse.setError(e.getClass().getName());
            jsonResponse.setPath("/api/device");
            log.error("error: {}", jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        } catch (ErrorService e) {
            ErrorResponse jsonResponse = new ErrorResponse();
            jsonResponse.setTimestamp(new Date());
            jsonResponse.setStatus(HttpStatus.BAD_REQUEST);
            jsonResponse.setMessage(e.getMessage());
            jsonResponse.setCode(400);
            jsonResponse.setError(e.getClass().getName());
            jsonResponse.setPath("/api/device");
            log.error("error: {}", jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Device>> getAllDevice() {
        Iterable<Device> deviceList = deviceServices.getAllDevice();
        log.info("status code: {}", HttpStatus.OK);
        return new ResponseEntity<Iterable<Device>>(deviceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/device/macAddress/{macAddress}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeviceByMacAddress(@PathVariable String macAddress) {
        Optional<Device> device = deviceServices.getDeviceByMacAddress(macAddress);
        if (device.isPresent())
            return new ResponseEntity<> (device, HttpStatus.OK);
        else {
            ErrorResponse jsonResponse = new ErrorResponse();
            jsonResponse.setTimestamp(new Date());
            jsonResponse.setStatus(HttpStatus.NOT_FOUND);
            jsonResponse.setMessage("device with id {"+macAddress+"} doesn't exist");
            jsonResponse.setCode(404);
            jsonResponse.setError("resource not available");
            jsonResponse.setPath("/api/device/macAddress");
            log.error("error: {}", jsonResponse);
            return new ResponseEntity<> (jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/device/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeviceById(@PathVariable Long id) {
            Optional<Device> device = deviceServices.getDeviceById(id);
            if (device.isPresent())
                return new ResponseEntity<> (device, HttpStatus.OK);
            else {
                ErrorResponse jsonResponse = new ErrorResponse();
                jsonResponse.setTimestamp(new Date());
                jsonResponse.setStatus(HttpStatus.NOT_FOUND);
                jsonResponse.setMessage("device with id {"+id+"} doesn't exist");
                jsonResponse.setCode(404);
                jsonResponse.setError("resource not available");
                jsonResponse.setPath("/api/device/id");
                log.error("error: {}", jsonResponse);
                return new ResponseEntity<> (jsonResponse, HttpStatus.NOT_FOUND);
            }
    }

    @RequestMapping(value = "/device/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDeviceById(@PathVariable String id) {
        try {
            deviceServices.deleteDeviceById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse jsonResponse = new ErrorResponse();
            jsonResponse.setTimestamp(new Date());
            jsonResponse.setStatus(HttpStatus.NOT_FOUND);
            jsonResponse.setMessage("device with id {"+id+"} doesn't exist");
            jsonResponse.setCode(409);
            jsonResponse.setError("resource not available");
            jsonResponse.setPath("/api/device");
            log.error("error: {}", jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

    }

}
