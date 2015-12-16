package javado;

import lombok.extern.log4j.Log4j2;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@Log4j2
public class HomePage extends WebPage {

  public HomePage(final PageParameters parameters) {
    super(parameters);

    add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
    add(new MyBookmarkablePageLink<>("toGeolocationPage", GeolocationPage.class));
    add(new MyBookmarkablePageLink<>("toWebSocketPage", WebSocketPage.class));

  }

  /**
   * Bodyを自己解決するBookmarkablePageLink
   * 
   * @author Hiroto Yamakawa
   */
  class MyBookmarkablePageLink<T> extends BookmarkablePageLink<T> {

    public <C extends Page> MyBookmarkablePageLink(String id, Class<C> pageClass, PageParameters parameters) {
      super(id, pageClass, parameters);
    }

    public <C extends Page> MyBookmarkablePageLink(String id, Class<C> pageClass) {
      super(id, pageClass);
    }

    @Override
    protected void onInitialize() {
      super.onInitialize();
      setBody(Model.of(getPageClass().getSimpleName()));
    }
  }

}
