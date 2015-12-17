package javado.page;

import java.io.IOException;

import lombok.val;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.apache.wicket.protocol.ws.api.registry.IKey;
import org.apache.wicket.protocol.ws.api.registry.SimpleWebSocketConnectionRegistry;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class IndividualWebSocketBehaviorPage extends HomePage {
  private static final long serialVersionUID = -4947990594333817869L;

  private String applicationName;
  private String sessionId;
  private IKey key;

  public IndividualWebSocketBehaviorPage(final PageParameters parameters) {
    super(parameters);

    add(new WebSocketBehavior() {
      private static final long serialVersionUID = 2893097575188387245L;

      @Override
      protected void onConnect(ConnectedMessage message) {
        applicationName = message.getApplication().getName();
        sessionId = message.getSessionId();
        key = message.getKey();

      }

      @Override
      protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {
        val registry = new SimpleWebSocketConnectionRegistry();
        val connection = registry.getConnection(Application.get(applicationName), sessionId, key);
        if (connection != null && connection.isOpen()) {
          try {
            connection.sendMessage(message.getText() + " : " + sessionId);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(JavaScriptHeaderItem.forUrl("js/AccuratePosition.js"));
  }

}
