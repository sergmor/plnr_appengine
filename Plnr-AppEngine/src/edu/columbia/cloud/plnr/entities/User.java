package edu.columbia.cloud.plnr.entities;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.GeoPt;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class User {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private String userName;
	@Persistent
	private GeoPt LocationHash;
	@Persistent
	private String locationName;
	@Persistent
	boolean tutor;
	@Persistent
	private List<Subject> subjects;
	@Persistent
	private Status status;
	@Persistent
	private TutorStatus tutorStatus;
	
	
	
	public User(Key key, String userName, GeoPt locationHash,
			String locationName, boolean tutor, List<Subject> subjects,
			Status status, TutorStatus tutorStatus) {		
		this.key = key;
		this.userName = userName;
		LocationHash = locationHash;
		this.locationName = locationName;
		this.tutor = tutor;
		this.subjects = subjects;
		this.status = status;
		this.tutorStatus = tutorStatus;
	}
	
	public User() {
		
	}
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public GeoPt getLocationHash() {
		return LocationHash;
	}
	public void setLocationHash(GeoPt locationHash) {
		LocationHash = locationHash;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public boolean isTutor() {
		return tutor;
	}
	public void setTutor(boolean tutor) {
		this.tutor = tutor;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public TutorStatus getTutorStatus() {
		return tutorStatus;
	}
	public void setTutorStatus(TutorStatus tutorStatus) {
		this.tutorStatus = tutorStatus;
	}
	
	
}
