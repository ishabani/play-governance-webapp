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
package controllers;

import models.Node;
import utils.Settings;

public class Application extends PlayController {

	private static String getURL() {
		Node n = Node.getCurrentNode();
		if (n == null) {
			flash.success("Please connect!");
			NodeController.connect();
		}
		return n.baseURL;
	}
	
	public static void index() {
		// redirect to topics
		//TopicsController.topics();
		render();
	}

	/**
	 * 
	 */
	public static void switchDebugMode() {
		Settings.switchDebug();
		flash.success("Debug mode is %s", Settings.getDebugMode());
		index();
	}
}
