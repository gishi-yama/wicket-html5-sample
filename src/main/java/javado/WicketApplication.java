package javado;

import javado.page.CallbackFunctionPage;
import javado.page.GeolocationPage;
import javado.page.HomePage;
import javado.page.IndividualWebSocketBehaviorPage;
import javado.page.WebPageAsAPI;
import javado.page.WebSocketBehaviorPage;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;

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
    pageMount(GeolocationPage.class);
    pageMount(WebSocketBehaviorPage.class);
    pageMount(IndividualWebSocketBehaviorPage.class);
    pageMount(CallbackFunctionPage.class);
    pageMount(WebPageAsAPI.class);
  }
  

  public void apiMount(Class<? extends Page> pageClass) {
    mount(new MountedMapper(pageClass.getSimpleName(), pageClass));
  }
  
  public void pageMount(Class<? extends Page> pageClass) {
    mount(new MountedMapper(pageClass.getSimpleName(), pageClass, new UrlPathPageParametersEncoder()));
  }

}
