package javado.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CallbackFunctionPage extends HomePage {
  private static final long serialVersionUID = 3240547016801539473L;

  private static final String FUNCTION_PREFIX = "sendToServer = ";
  private static final String PARAM1 = "latitude";
  private static final String PARAM2 = "longitude";
  private static final String PARAM3 = "accuracy";

  public CallbackFunctionPage() {
    super(null);

    IModel<List<String>> logsModel = Model.ofList(new ArrayList<>());

    val wmc = new WebMarkupContainer("wmc") {
      private static final long serialVersionUID = -2227830433028296454L;

      @Override
      protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
      }
    };
    add(wmc);

    wmc.add(new ListView<String>("logs", logsModel) {
      private static final long serialVersionUID = -8890866337333879494L;

      @Override
      protected void populateItem(ListItem<String> item) {
        item.add(new Label("message", item.getModel()));
      }
    });

    // Wicketによる JavaScript function（serverTosend =...）を作成するBehavior
    add(new AbstractDefaultAjaxBehavior() {
      private static final long serialVersionUID = 5951711733278478L;

      @Override
      public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        // functionの中身をを生成する
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
        // functionに渡された引数を取得する
        val callBaclParms = getRequest().getRequestParameters();
        val message = Stream.of(PARAM1, PARAM2, PARAM3)
            .map(name -> name + ":" + callBaclParms.getParameterValue(name).toString())
            .collect(Collectors.joining(" "));
        System.out.println(message);

        // 送信されたメッセージをlogsModelに追加し、ListViewをAjaxで再描画する
        logsModel.getObject().add(message);
        target.add(wmc);
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
