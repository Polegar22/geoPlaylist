package fr.cnam.projetSiw.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import fr.cnam.projetSiw.client.SparqlService;

@SuppressWarnings("serial")
public class SparqlServiceImpl extends RemoteServiceServlet implements SparqlService{
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
