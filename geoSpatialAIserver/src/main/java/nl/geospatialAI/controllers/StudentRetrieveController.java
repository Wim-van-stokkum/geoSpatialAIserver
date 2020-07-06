package nl.geospatialAI.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.Student;
import nl.geospatialAI.beans.StudentRegistration;



@Controller

public class StudentRetrieveController {

  @RequestMapping(method = RequestMethod.GET, value="/geoSpatialAIserver/student/allstudent")

  @ResponseBody

  public List<Student> getAllStudents() {

  return StudentRegistration.getInstance().getStudentRecords();

  }

}