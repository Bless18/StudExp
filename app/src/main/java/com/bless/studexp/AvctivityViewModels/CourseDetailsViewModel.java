package com.bless.studexp.AvctivityViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bless.studexp.models.Course;

public class CourseDetailsViewModel  extends ViewModel {
  private MutableLiveData<Course>  course;
   public  CourseDetailsViewModel(){
        course=new MutableLiveData<>();
    }
    public LiveData<Course> getCourse(Course courses){
      course.setValue(courses);
       return  course;
    }
}
