package javado;

import javado.page.GeolocationPage;
import javado.page.HomePage;
import javado.page.IndividualWebSocketBehaviorPage;
import javado.page.WebSocketBehaviorPage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see javado.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
  /**
   * @see org.apache.wicket.Application#getHomePage()
   */
  @Override
  public Class<? extends WebPage> getHomePage() {
    return HomePage.class;
  }

  /**
   * @see org.apache.wicket.Application#init()
   */
  @Override
  public void init() {
    super.init();
    mount(GeolocationPage.class);
    mount(WebSocketBehaviorPage.class);
    mount(IndividualWebSocketBehaviorPage.class);
  }
  
  public void mount(Class<? extends Page> pageClass) {
    mountPage(pageClass.getSimpleName(), pageClass);
  }

}
