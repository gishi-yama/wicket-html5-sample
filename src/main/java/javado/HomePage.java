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
  private static final long serialVersionUID = -2545158271026437999L;

  public HomePage(final PageParameters parameters) {
    super(parameters);
    add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
    add(new MyBookmarkablePageLink<>("toGeolocationPage", GeolocationPage.class));
    add(new MyBookmarkablePageLink<>("toWebSocketBehaviorPage", WebSocketBehaviorPage.class));
    add(new MyBookmarkablePageLink<>("toIndividualWebSocketBehaviorPage", IndividualWebSocketBehaviorPage.class));
    add(new Label("pageName", getPageName()));
  }

  public String getPageName() {
    return getClass().getSimpleName();
  }

  /**
   * Bodyを自己解決するBookmarkablePageLink
   * 
   * @author Hiroto Yamakawa
   */
  class MyBookmarkablePageLink<T> extends BookmarkablePageLink<T> {
    private static final long serialVersionUID = 7460356322167599994L;

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
