package javado;

import lombok.val;
import lombok.extern.log4j.Log4j2;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.geolocation.AjaxGeolocationBehavior;

@Log4j2
public class HomePage extends WebPage {
  private static final long serialVersionUID = 1L;

  public HomePage(final PageParameters parameters) {
    super(parameters);

    add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

    val geolocator = new WebMarkupContainer("geolocator");
    add(geolocator);

    geolocator.add(new AjaxGeolocationBehavior() {

      @Override
      protected void onGeoAvailable(AjaxRequestTarget target, String latitude, String longitude) {
        System.out.println(System.currentTimeMillis() + "We received a location. Latitude: " + latitude + " Longitude: " + longitude);
      }

      @Override
      protected void onNotAvailable(AjaxRequestTarget target, String errorCode, String errorMessage) {
        System.out.println(System.currentTimeMillis() + "An error occured. Error code: " + errorCode + " Error message:" + errorMessage);
      }

      @Override
      public int getTimeout() {
        // milliseconds
        return 5000;
      }

    });
    // 複数回送信
    // geolocator.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5L)));

  }
}
