package controllers;

import java.util.List;

import models.Node;

import org.petalslink.dsb.cxf.CXFHelper;

import play.mvc.Before;
import play.mvc.Controller;
import eu.playproject.governance.api.EventGovernance;
import eu.playproject.governance.api.GovernanceExeption;
import eu.playproject.governance.api.TopicMetadataService;
import eu.playproject.governance.api.bean.Metadata;
import eu.playproject.governance.api.bean.Topic;

public class Application extends Controller {

	@Before
	public static void init() {
		Node n = Node.getCurrentNode();
		if (n != null) {
			renderArgs.put("currentNode", n);
		}
	}

	public static void connect() {
		List<Node> nodes = Node.all().fetch();
		render(nodes);
	}

	public static void nodeConnect(Long id) {
		Node n = Node.findById(id);
		if (n != null) {
			session.put("node", n.id);
			flash.success("Connected to node %s", n.name);
		} else {
			flash.error("No such node");
		}
		connect();
	}

	public static void nodeDisconnect(Long id) {
		session.remove("node");
		flash.success("Disconnected...");
		connect();
	}

	private static String getURL() {
		Node n = Node.getCurrentNode();
		if (n == null) {
			flash.success("Please connect!");
			connect();
		}
		return n.baseURL;
	}

	public static void index() {
		topics();
	}

	public static void topics() {
		EventGovernance client = CXFHelper.getClient(getURL(),
				EventGovernance.class);
		List<Topic> topics = null;
		try {
			topics = client.getTopics();
		} catch (Exception e) {
			flash.error("Unable to get topics '%s'", e.getMessage());
		}
		render(topics);
	}

	public static void topic(String name, String ns, String prefix) {
		Topic topic = new Topic();
		topic.setName(name);
		topic.setNs(ns);
		topic.setPrefix(prefix);

		TopicMetadataService client = CXFHelper.getClient(getURL(),
				TopicMetadataService.class);
		List<Metadata> meta = null;
		try {
			meta = client.getMetaData(topic);
		} catch (Exception e) {
			flash.error("Unable to get metadata for topic %s", e.getMessage());
		}
		render(topic, meta);
	}

	public static void createTopic(String name, String ns, String prefix) {
		EventGovernance client = CXFHelper.getClient(getURL(),
				EventGovernance.class);
		Topic topic = new Topic();
		topic.setName(name);
		topic.setNs(ns);
		topic.setPrefix(prefix);
		try {
			client.createTopic(topic);
		} catch (Exception e) {
			flash.error("Can not create topic '%s'", e.getMessage());
		}
		topics();
	}

	public static void addMeta(String name, String ns, String prefix,
			String mname, String mvalue) {
		TopicMetadataService client = CXFHelper.getClient(getURL(),
				TopicMetadataService.class);
		Topic topic = new Topic();
		topic.setName(name);
		topic.setNs(ns);
		topic.setPrefix(prefix);

		Metadata meta = new Metadata();
		meta.setName(mname);
		meta.setValue(mvalue);
		try {
			client.addMetadata(topic, meta);
		} catch (Exception e) {
			flash.error("Unable to get metadata for topic %s", e.getMessage());
		}
		topic(name, ns, prefix);
	}
}