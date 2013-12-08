package edu.columbia.cloud.plnr.entities;

import java.sql.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
@PersistenceCapable
public class StudyGroup {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private GeoPt locationHash;
	@Persistent
	private String locationName;
	@Persistent
	private Date startTime;
	@Persistent
	private Duration duration;
	@Persistent
	private List<User> attendees;
	@Persistent
	private Subject subject;
	
	public StudyGroup(Key key, GeoPt locationHash, String locationName,
			Date startTime, Duration duration, List<User> attendees,
			Subject subject) {
		super();
		this.key = key;
		this.locationHash = locationHash;
		this.locationName = locationName;
		this.startTime = startTime;
		this.duration = duration;
		this.attendees = attendees;
		this.subject = subject;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public GeoPt getLocationHash() {
		return locationHash;
	}
	public void setLocationHash(GeoPt locationHash) {
		this.locationHash = locationHash;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public List<User> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
	
}
