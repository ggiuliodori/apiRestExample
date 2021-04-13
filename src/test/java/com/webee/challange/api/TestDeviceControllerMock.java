package com.webee.challange.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webee.challange.repository.models.Device;
import com.webee.challange.services.DeviceServices;
import com.webee.challange.services.ErrorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Date;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestDeviceControllerMock {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceServices deviceServicesMock;

    @Test
    public void getAllDevices() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(Long.valueOf(1));
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        Mockito.when(deviceServicesMock.getAllDevice()).thenReturn(Collections.singleton(mockDevice));
        mvc.perform(get("/api/device").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDevicesById() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        Mockito.when(deviceServicesMock.getDeviceById(mockDevice.getId())).thenReturn(java.util.Optional.of((mockDevice)));
        mvc.perform(get("http://localhost:8080/api/device/id/"+mockDevice.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDevicesByIdThatNotExist() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        Mockito.when(deviceServicesMock.getDeviceById(2L)).thenReturn(java.util.Optional.of((mockDevice)));
        mvc.perform(get("http://localhost:8080/api/device/id/"+mockDevice.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDevicesByMacAddress() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        Mockito.when(deviceServicesMock.getDeviceByMacAddress(mockDevice.getMacAddress())).thenReturn(java.util.Optional.of(mockDevice));
        mvc.perform(get("http://localhost:8080/api/device/macAddress/"+mockDevice.getMacAddress()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDevicesByMacAddressThatNotExist() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        Mockito.when(deviceServicesMock.getDeviceByMacAddress("FF:EE:DD:CC:BB:AA")).thenReturn(java.util.Optional.of((mockDevice)));
        mvc.perform(get("http://localhost:8080/api/device/macAddress/"+mockDevice.getMacAddress()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDevicesById() throws Exception {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(mockDevice);
        mvc.perform(post("http://localhost:8080/api/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));
        Mockito.doNothing().when(deviceServicesMock).deleteDeviceById(1L);
        mvc.perform(delete("http://localhost:8080/api/device/"+mockDevice.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDevicesByIdThatNotExist() throws Exception {
        Mockito.doThrow(new Exception()).when(deviceServicesMock).deleteDeviceById(1L);
        mvc.perform(delete("http://localhost:8080/api/device/"+1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addDevice() throws Exception, ErrorService {
        Device mockDevice = new Device();
        Date timestamp = new Date();
        mockDevice.setId(1L);
        mockDevice.setMacAddress("AA:BB:CC:DD:EE:FF");
        mockDevice.setTimestamp(timestamp);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(mockDevice);
        mvc.perform(post("http://localhost:8080/api/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());
    }
}
