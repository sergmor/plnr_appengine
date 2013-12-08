package edu.columbia.cloud.plnr.entities;

import edu.columbia.cloud.plnr.entities.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "studygroupendpoint", namespace = @ApiNamespace(ownerDomain = "columbia.edu", ownerName = "columbia.edu", packagePath = "cloud.plnr.entities"))
public class StudyGroupEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listStudyGroup")
	public CollectionResponse<StudyGroup> listStudyGroup(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<StudyGroup> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(StudyGroup.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<StudyGroup>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (StudyGroup obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<StudyGroup> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getStudyGroup")
	public StudyGroup getStudyGroup(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		StudyGroup studygroup = null;
		try {
			studygroup = mgr.getObjectById(StudyGroup.class, id);
		} finally {
			mgr.close();
		}
		return studygroup;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param studygroup the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertStudyGroup")
	public StudyGroup insertStudyGroup(StudyGroup studygroup) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsStudyGroup(studygroup)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(studygroup);
		} finally {
			mgr.close();
		}
		return studygroup;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param studygroup the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateStudyGroup")
	public StudyGroup updateStudyGroup(StudyGroup studygroup) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsStudyGroup(studygroup)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(studygroup);
		} finally {
			mgr.close();
		}
		return studygroup;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeStudyGroup")
	public void removeStudyGroup(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			StudyGroup studygroup = mgr.getObjectById(StudyGroup.class, id);
			mgr.deletePersistent(studygroup);
		} finally {
			mgr.close();
		}
	}

	private boolean containsStudyGroup(StudyGroup studygroup) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(StudyGroup.class, studygroup.getKey());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
