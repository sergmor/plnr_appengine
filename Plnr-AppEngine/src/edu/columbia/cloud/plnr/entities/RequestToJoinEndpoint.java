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

@Api(name = "requesttojoinendpoint", namespace = @ApiNamespace(ownerDomain = "columbia.edu", ownerName = "columbia.edu", packagePath = "cloud.plnr.entities"))
public class RequestToJoinEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRequestToJoin")
	public CollectionResponse<RequestToJoin> listRequestToJoin(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<RequestToJoin> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(RequestToJoin.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<RequestToJoin>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (RequestToJoin obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<RequestToJoin> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getRequestToJoin")
	public RequestToJoin getRequestToJoin(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		RequestToJoin requesttojoin = null;
		try {
			requesttojoin = mgr.getObjectById(RequestToJoin.class, id);
		} finally {
			mgr.close();
		}
		return requesttojoin;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param requesttojoin the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRequestToJoin")
	public RequestToJoin insertRequestToJoin(RequestToJoin requesttojoin) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsRequestToJoin(requesttojoin)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(requesttojoin);
		} finally {
			mgr.close();
		}
		return requesttojoin;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param requesttojoin the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRequestToJoin")
	public RequestToJoin updateRequestToJoin(RequestToJoin requesttojoin) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsRequestToJoin(requesttojoin)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(requesttojoin);
		} finally {
			mgr.close();
		}
		return requesttojoin;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRequestToJoin")
	public void removeRequestToJoin(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			RequestToJoin requesttojoin = mgr.getObjectById(
					RequestToJoin.class, id);
			mgr.deletePersistent(requesttojoin);
		} finally {
			mgr.close();
		}
	}

	private boolean containsRequestToJoin(RequestToJoin requesttojoin) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(RequestToJoin.class, requesttojoin.getKey());
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
