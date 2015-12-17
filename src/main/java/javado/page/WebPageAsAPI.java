package javado.page;

import java.nio.charset.StandardCharsets;

import lombok.val;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.http.WebResponse;

public class WebPageAsAPI extends WebPage {

  private static final String CONTENT_TYPE = "application/json";

  public WebPageAsAPI() {
    val params = getRequest().getPostParameters();
    val param1 = params.getParameterValue("param1").toString("none");
    System.out.println(params.getParameterNames());
    System.out.println(param1);

    RequestCycle.get().scheduleRequestHandlerAfterCurrent(
        new TextRequestHandler(CONTENT_TYPE, StandardCharsets.UTF_8.toString(),
            "{\"param1\":\"" + param1 + "\"}"));
  }

  @Override
  protected void configureResponse(WebResponse response) {
    super.configureResponse(response);
    response.setContentType(CONTENT_TYPE);
  }

}
