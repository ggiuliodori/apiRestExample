package com.webee.challange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {

    Optional<Device> findByMacAddress(String macAddress);


}
