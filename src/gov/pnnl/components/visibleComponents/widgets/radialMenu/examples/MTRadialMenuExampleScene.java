/**
 * This material was prepared as an account of work sponsored by an agency of the United States Government.<br>
 * Neither the United States Government nor the United States Department of Energy, nor any of their employees,<br> 
 * nor any of their contractors, subcontractors or their employees, makes any warranty, express or implied, or<br>
 * assumes any legal liability or responsibility for the accuracy, completeness, or usefulness or any information,<br> 
 * apparatus, product, or process disclosed, or represents that its use would not infringe privately owned rights.
 */
package gov.pnnl.components.visibleComponents.widgets.radialMenu.examples;

import gov.pnnl.components.visibleComponents.widgets.radialMenu.MTRadialMenu;
import gov.pnnl.components.visibleComponents.widgets.radialMenu.item.MTMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.interfaces.IMTComponent3D;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTPolygon;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.inputData.MTFingerInputEvt;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.animation.AnimationEvent;
import org.mt4j.util.animation.IAnimation;
import org.mt4j.util.animation.IAnimationListener;
import org.mt4j.util.animation.ani.AniAnimation;
import org.mt4j.util.math.Vector3D;
import org.mt4jx.util.animation.AnimationUtil;

/**
 * The example scene which contains a number of components and their radial menus
 * 
 * @author Ryan LaMothe
 * 
 */
public class MTRadialMenuExampleScene extends AbstractScene {

	// Logger for this class
	private static final Logger LOG = Logger.getLogger(MTRadialMenuExampleScene.class);

	private MTRadialMenu mtRadialMenu1;

	private MTRadialMenu mtRadialMenu2;

	MTApplication mtApplication;

	MTRectangle mtRectangle;

	boolean translate = true;

	/**
	 * Default constructor
	 * 
	 * @param mtApplication
	 * @param name
	 */
	public MTRadialMenuExampleScene(final MTApplication mtApplication, final String name) {
		super(mtApplication, name);
		this.mtApplication = mtApplication;

		/*
		 * Color used to clear the scene's canvas before every new frame is drawn
		 */
		this.setClearColor(new MTColor(146, 150, 188, 255));

		/*
		 * Adds a circle to the canvas at the cursor's position
		 */
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this));

		/*
		 * Create a rectangle
		 */
		final MTRectangle mtRectangle = new MTRectangle(mtApplication, 875, 100);
		this.mtRectangle = mtRectangle;
		mtRectangle.setFillColor(MTColor.BLACK);
		mtRectangle.setPositionGlobal(new Vector3D(mtApplication.width / 2f, mtApplication.height / 2f));
		mtRectangle.setName("rectangle");

		this.getCanvas().addChild(mtRectangle);

		/*
		 * Create a TextArea
		 * 
		 * Fonts are managed by the FontManager singleton class
		 * If a TrueType font is specified, a vector font is created
		 */


		/*
		 * Create a TextArea
		 * 
		 * Fonts are managed by the FontManager singleton class
		 * If a TrueType font is specified, a vector font is created
		 */
		final MTTextArea textArea2 = new MTTextArea(mtApplication,
				FontManager.getInstance().createFont(mtApplication, "arial.ttf",
						50, // Font size
						new MTColor(255, 255, 255, 255), // Font fill color
						true)); // Anti-alias

		textArea2.setName("textArea");

		/*
		 * Since the TextArea is a rectangle we only want to show the text
		 * So we set properties which will hide the rectangle
		 */
		textArea2.setNoFill(true);
		textArea2.setNoStroke(true);

		/*
		 * Set the Text in this component
		 */
		textArea2.setText("Click Here To See a Component Menu");
		textArea2.removeAllGestureEventListeners();

		/*
		 * Place the text area in the center of the rectangle
		 */
		textArea2.setPositionRelativeToParent(new Vector3D(mtRectangle.getCenterPointLocal().x, mtRectangle.getCenterPointLocal().y));
		mtRectangle.addChild(textArea2);

		createMenu();


	}

	final IMTInputEventListener rotateInputListener = new IMTInputEventListener() {
		@Override
		public boolean processInputEvent(final MTInputEvent inEvt) {

			// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
			if (inEvt instanceof MTFingerInputEvt) {
				final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
				switch (cursorInputEvt.getId()) {
				case TapEvent.GESTURE_STARTED:
				{
					AnimationUtil.rotate2D(mtRectangle, 720);
				}
				break;
				default:
					break;
				}
			} else {
				LOG.warn("Some other event occured:" + inEvt);
			}
			return false;
		}
	};

	final IMTInputEventListener scaleInInputListener = new IMTInputEventListener() {
		@Override
		public boolean processInputEvent(final MTInputEvent inEvt) {

			// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
			if (inEvt instanceof MTFingerInputEvt) {
				final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
				switch (cursorInputEvt.getId()) {
				case TapEvent.GESTURE_STARTED:
				{
					AnimationUtil.scaleIn(mtRectangle);
				}
				break;
				default:
					break;
				}
			} else {
				LOG.warn("Some other event occured:" + inEvt);
			}
			return false;
		}
	};	

	final IMTInputEventListener changeTextInputListener = new IMTInputEventListener() {
		@Override
		public boolean processInputEvent(final MTInputEvent inEvt) {

			// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
			if (inEvt instanceof MTFingerInputEvt) {
				final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
				switch (cursorInputEvt.getId()) {
				case TapEvent.GESTURE_STARTED:
				{
					final MTTextArea textArea = (MTTextArea) mtRectangle.getChildByName("textArea");
					textArea.setText("New Text");
				}
				break;
				default:
					break;
				}
			} else {
				LOG.warn("Some other event occured:" + inEvt);
			}
			return false;
		}
	};
	
	final IMTInputEventListener elasticInInputListener = new IMTInputEventListener() {
		@Override
		public boolean processInputEvent(final MTInputEvent inEvt) {

			// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
			if (inEvt instanceof MTFingerInputEvt) {
				final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
				switch (cursorInputEvt.getId()) {
				case TapEvent.GESTURE_STARTED:
				{
					elasticInOut(mtRectangle);
				}
				break;
				default:
					break;
				}
			} else {
				LOG.warn("Some other event occured:" + inEvt);
			}
			return false;
		}
	};
	
	private void createMenu() {
		/*
		 * Create a number of inputlisteners to attach to components
		 */

		final float translateOrigX = mtRectangle.getCenterPointLocal().getX();
		final float translateOrigY = mtRectangle.getCenterPointLocal().getY();

		// FIXME Change the listener to reset component position back to original location every other time
		final IMTInputEventListener translateInputListener = new IMTInputEventListener() {
			@Override
			public boolean processInputEvent(final MTInputEvent inEvt) {

				// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
				if (inEvt instanceof MTFingerInputEvt) {
					final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
					switch (cursorInputEvt.getId()) {
					case TapEvent.GESTURE_STARTED:
					{
						if (MTRadialMenuExampleScene.this.isTranslate())
						{
							final Random random = new Random();
							float x = random.nextInt(mtApplication.width) / 2;
							if (x % 2 == 0)
							{
								x = -x;
							}

							float y = random.nextInt(mtApplication.height) / 2;
							if (y % 2 == 0)
							{
								y = -y;
							}

							AnimationUtil.translate(mtRectangle, x, y);

							MTRadialMenuExampleScene.this.setTranslate(false);
						}
						else
						{
							mtRectangle.translate(new Vector3D(translateOrigX, translateOrigY));
							MTRadialMenuExampleScene.this.setTranslate(true);
						}
					}
					break;
					default:
						break;
					}
				} else {
					LOG.warn("Some other event occured:" + inEvt);
				}
				return false;
			}
		};

		/*
		 * Add a Radial Menu to the rectangle
		 */
		final IMTInputEventListener rectangleInputListener = new IMTInputEventListener() {
			@Override
			public boolean processInputEvent(final MTInputEvent inEvt) {

				// Most input events in MT4j are an instance of AbstractCursorInputEvt (mouse, multi-touch..)
				if (inEvt instanceof MTFingerInputEvt) {
					final MTFingerInputEvt cursorInputEvt = (MTFingerInputEvt) inEvt;
					final IMTComponent3D target = cursorInputEvt.getTarget();
					switch (cursorInputEvt.getId()) {
					case TapEvent.GESTURE_STARTED:

						if (target.getName().equals("rectangle") || target.getName().equals("textArea"))
						{
							if ((MTRadialMenuExampleScene.this.mtRadialMenu2 != null) && !MTRadialMenuExampleScene.this.mtRadialMenu2.isVisible())
							{
								MTRadialMenuExampleScene.this.mtRadialMenu2.destroy();
								MTRadialMenuExampleScene.this.mtRadialMenu2 = null;
							}

							if (MTRadialMenuExampleScene.this.mtRadialMenu2 == null)
							{
								// Build list of menu items
								final List<MTMenuItem> menuItems = new ArrayList<MTMenuItem>();

								final MTMenuItem menu1 = new MTMenuItem("File", null);
								menu1.addSubMenuItem(new MTMenuItem("New Session", null));
								menu1.addSubMenuItem(new MTMenuItem("Open Session", null));
								menu1.addSubMenuItem(new MTMenuItem("Save Session", null));
								menu1.addSubMenuItem(new MTMenuItem("Properties", null));

								final MTMenuItem menu2 = new MTMenuItem("Edit", null);
								menu2.addSubMenuItem(new MTMenuItem("Undo Action", null));
								menu2.addSubMenuItem(new MTMenuItem("Redo Action", null));
								menu2.addSubMenuItem(new MTMenuItem("Select All", null));
								menu2.addSubMenuItem(new MTMenuItem("Cut", null));
								menu2.addSubMenuItem(new MTMenuItem("Copy", null));
								menu2.addSubMenuItem(new MTMenuItem("Paste", null));

								final MTMenuItem menu3 = new MTMenuItem("Actions", null);

								final MTMenuItem subMenu31 = new MTMenuItem("Rotate", null);
								subMenu31.addInputEventListener(rotateInputListener);
								menu3.addSubMenuItem(subMenu31);

								final MTMenuItem subMenu32 = new MTMenuItem("Scale In", null);
								subMenu32.addInputEventListener(scaleInInputListener);
								menu3.addSubMenuItem(subMenu32);

								final MTMenuItem subMenu33 = new MTMenuItem("Translate", null);
								subMenu33.addInputEventListener(translateInputListener);
								menu3.addSubMenuItem(subMenu33);

								final MTMenuItem subMenu34 = new MTMenuItem("Elastic", null);
								subMenu34.addInputEventListener(elasticInInputListener);
								menu3.addSubMenuItem(subMenu34);

								final MTMenuItem subMenu35 = new MTMenuItem("Change Text", null);
								subMenu35.addInputEventListener(changeTextInputListener);
								menu3.addSubMenuItem(subMenu35);

								final MTMenuItem menu4 = new MTMenuItem("View", null);
								final MTMenuItem menu5 = new MTMenuItem("Filter", null);

								menuItems.add(menu1);
								menuItems.add(menu2);
								menuItems.add(menu3);
								menuItems.add(menu4);
								menuItems.add(menu5);

								// Create menu
								final Vector3D vector = new Vector3D(cursorInputEvt.getX(), cursorInputEvt.getY());

								final IFont font = FontManager.getInstance().createFont(mtApplication, "arial.ttf",
										16, // Font size
										new MTColor(255, 255, 255, 255), // Font fill color
										true); // Anti-alias

								MTRadialMenuExampleScene.this.mtRadialMenu2 = new MTRadialMenu(mtApplication, vector, font, 1f, menuItems);
								MTRadialMenuExampleScene.this.getCanvas().addChild(MTRadialMenuExampleScene.this.mtRadialMenu2);
							}
						}
						break;
					default:
						break;
					}
				} else {
					LOG.warn("Some other event occured:" + inEvt);
				}
				return false;
			}
		};

		mtRectangle.addInputListener(rectangleInputListener);
	}

	/**
	 * {@link AnimationUtil} for additional animation examples
	 * 
	 * @param as
	 */
	private static void elasticInOut(final MTPolygon as) {
		final float width = as.getWidthXY(TransformSpace.RELATIVE_TO_PARENT);
		final IAnimation closeAnim = new AniAnimation(1, width, 5000, AniAnimation.ELASTIC_IN_OUT, as);

		closeAnim.addAnimationListener(new IAnimationListener() {
			@Override
			public void processAnimationEvent(final AnimationEvent ae) {
				// float delta = ae.getAnimation().getInterpolator().getCurrentStepDelta();
				switch (ae.getId()) {
				case AnimationEvent.ANIMATION_STARTED:
				case AnimationEvent.ANIMATION_UPDATED:
				{
					final float currentVal = ae.getValue();
					as.setWidthXYRelativeToParent(currentVal);
					break;
				}
				case AnimationEvent.ANIMATION_ENDED:
					break;
				default:
					break;
				}// switch
			}// processanimation
		});

		closeAnim.start();
	}

	public boolean isTranslate() {
		return this.translate;
	}

	public void setTranslate(final boolean translate) {
		this.translate = translate;
	}
}