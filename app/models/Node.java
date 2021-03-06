/**
 *
 * Copyright (c) 2013, Linagora
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 *
 */
package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.mvc.Scope.Session;
import utils.Settings;

/**
 * @author chamerling
 * 
 */
@Entity
public class Node extends Model {

	public String name;

	public String baseURL;

	public boolean isSame(String id) {
		return id != null && Long.valueOf(id).equals(this.id);
	}

	public static Node getCurrentNode() {
		String nodeId = Session.current().get("node");
		if (nodeId == null) {
			return null;
		}
		return Node.findById(Long.valueOf(nodeId));
	}

	public static Node getDefault() {
		return Node.find("name", Settings.DEFAULT_REGISTRY).first();
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", baseURL=" + baseURL + "]";
	}

}
