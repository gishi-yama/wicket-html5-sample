package javado.page;

import java.util.stream.Stream;

import lombok.val;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class CallbackFunctionPage extends HomePage {

  private static final String FUNCTION_PREFIX = "sendToServer = ";
  private static final String PARAM1 = "latitude";
  private static final String PARAM2 = "longitude";
  private static final String PARAM3 = "accuracy";

  public CallbackFunctionPage() {
    super(null);

    add(new AbstractDefaultAjaxBehavior() {

      @Override
      public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        CharSequence function = getCallbackFunction(
            CallbackParameter.explicit(PARAM1),
            CallbackParameter.explicit(PARAM2),
            CallbackParameter.explicit(PARAM3)
            );
        String js = FUNCTION_PREFIX + function.toString();
        response.render(OnDomReadyHeaderItem.forScript(js));
      }

      @Override
      protected void respond(AjaxRequestTarget target) {
        val callBaclParms = getRequest().getRequestParameters();
        Stream.of(PARAM1, PARAM2, PARAM3)
            .map(name -> name + ":" + callBaclParms.getParameterValue(name).toString())
            .forEach(System.out::println);
      }

    });
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(JavaScriptHeaderItem.forUrl("js/AccuratePosition.js"));
    response.render(JavaScriptHeaderItem.forUrl("js/CallbackFunction.js"));
  }

}
