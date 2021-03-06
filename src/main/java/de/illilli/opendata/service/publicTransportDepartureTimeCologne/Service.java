package de.illilli.opendata.service.publicTransportDepartureTimeCologne;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import de.illilli.opendata.service.Config;
import de.illilli.opendata.service.Facade;

@Path("/")
public class Service {

	private final static Logger logger = Logger.getLogger(Service.class);
	public static final String ENCODING = Config.getProperty("encoding");

	private long date = 0;

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	/**
	 * <p>
	 * Beispiele:
	 * <ul>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/stop/2">
	 * /publicTransportDepartureTimeCologne/service/stop/2</a> </il>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/stop/2?datatables">
	 * /publicTransportDepartureTimeCologne/service/stop/2?datatables</a></li>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/stop/2?fromTo=50.940214,6.953710,50.940356,6.961413">
	 * /publicTransportDepartureTimeCologne/service/stop/2?fromTo=50.940214,6.
	 * 953710,50.940356,6.961413</a></li>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/stop/2?fromTo=50.940214,6.953710,50.940356,6.961413&datatables">
	 * /publicTransportDepartureTimeCologne/service/stop/2?fromTo=50.940214,6.
	 * 953710,50.940356,6.961413&datatables</a></li>
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/stop/{id}")
	public String getStop(@PathParam("id") int id) throws IOException, ParseException {

		request.setCharacterEncoding(ENCODING);
		response.setCharacterEncoding(ENCODING);

		boolean datatables = request.getParameter("datatables") != null;
		String fromTo = request.getParameter("fromTo");

		Facade facade = null;
		if (datatables) {
			if (fromTo != null) {
				facade = new DepartureDatatablesFacade(id, fromTo);
				logger.info("call '/publicTransportDepartureTimeCologne/service/stop/" + id + "?fromTo" + fromTo
						+ "&datatables'");
			} else {
				facade = new DepartureDatatablesFacade(id);
				logger.info("call '/publicTransportDepartureTimeCologne/service/stop/" + id + "&datatables'");
			}
		} else {
			logger.info("call '/publicTransportDepartureTimeCologne/service/stop/" + id + "'");
			if (fromTo != null) {
				facade = new DepartureFacade(id, fromTo);
				logger.info(
						"call '/publicTransportDepartureTimeCologne/service/stop/" + id + "?fromTo=" + fromTo + "'");
			} else {
				facade = new DepartureFacade(id);
				logger.info("call '/publicTransportDepartureTimeCologne/service/stop/" + id + "'");
			}
		}

		return facade.getJson();
	}

	/**
	 * <p>
	 * Fragt Daten eines Fahrrads ab. Wenn fromto übergeben wird, wird
	 * zusätzlich die Zeit übermittelt, die notwendig ist, um das Fahrrad zu
	 * erreichen.
	 * </p>
	 * <p>
	 * Beispiele:
	 * <ul>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/bike/22336">
	 * /publicTransportDepartureTimeCologne/service/bike/{id}</a></li>
	 * <li><a href=
	 * "http://localhost:8080/publicTransportDepartureTimeCologne/service/bike/22336?fromTo=50.940214,6.953710,50.940356,6.961413">
	 * /publicTransportDepartureTimeCologne/service/bike/{id}?fromTo={lat,lng,
	 * lat,lng}</a></li>
	 * </ul>
	 * </p>
	 * 
	 * @param id
	 *            Nummer anhand das Fahrrad erkannt wird.
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/bike/{id}")
	public String getBike(@PathParam("id") int id) throws IOException, ParseException {

		request.setCharacterEncoding(ENCODING);
		response.setCharacterEncoding(ENCODING);

		String fromTo = request.getParameter("fromTo");

		Facade facade = null;

		if (fromTo != null) {
			facade = new BikeFacade(id, fromTo);
			logger.info("call '/publicTransportDepartureTimeCologne/service/bike/" + id + "?fromTo=" + fromTo + "'");
		} else {
			facade = new BikeFacade(id);
			logger.info("call '/publicTransportDepartureTimeCologne/service/bike/" + id + "'");
		}

		return facade.getJson();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/ping")
	public String getPing() throws MalformedURLException, IOException {

		request.setCharacterEncoding(ENCODING);
		response.setCharacterEncoding(ENCODING);

		return "{alive}";
	}

}
