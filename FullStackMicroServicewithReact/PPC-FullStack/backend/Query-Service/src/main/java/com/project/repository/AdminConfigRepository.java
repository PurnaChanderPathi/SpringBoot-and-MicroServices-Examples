package com.project.repository;

import com.project.entity.AdminConfig;

import java.util.List;

public interface AdminConfigRepository {

    List<String> getGroupNames();

    List<String> getDivisions(String groupName);

    List<String> getSpocs(String division);
}
