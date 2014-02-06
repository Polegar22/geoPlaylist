package fr.cnam.projetSiw.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import fr.cnam.projetSiw.client.GreetingService;
import fr.cnam.projetSiw.shared.FieldVerifier;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String getSparqlDatas() {
		String queryStr = "SELECT * WHERE{ <http://dbpedia.org/resource/Barack_Obama_social_policy> ?p ?o } LIMIT 10 ";
	       Query query = QueryFactory.create(queryStr);

	       // Remote execution.
	       QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	       // Set the DBpedia specific timeout.
	       ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

	       // Execute.
	       ResultSet rs = qexec.execSelect();
//	       ResultSetFormatter.out(System.out, rs, query);
	       return ResultSetFormatter.asText(rs, query);	 
	}
}
