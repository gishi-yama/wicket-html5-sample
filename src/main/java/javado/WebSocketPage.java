package javado;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WebSocketPage extends HomePage {

  public WebSocketPage(final PageParameters parameters) {
    super(parameters);

    add(new WebSocketBehavior() {

      @Override
      protected void onConnect(ConnectedMessage message) {
        super.onConnect(message);
        System.out.println(message);
      }

      @Override
      protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {
        String msg = message.getText();
        System.out.println(msg);
      }

    });
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    // response.render(JavaScriptHeaderItem.forReference(WicketWebSocketJQueryResourceReference.get()));
    response.render(JavaScriptHeaderItem.forUrl("js/WebSocketPage.js"));

  }

}
