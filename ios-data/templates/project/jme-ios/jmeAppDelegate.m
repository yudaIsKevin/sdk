#include <stdint.h>
#import "jmeAppDelegate.h"

static JNIEnv*
getEnv(JavaVM* vm)
{
    void* env;
    if ((*vm)->GetEnv(vm, &env, JNI_VERSION_1_4) == JNI_OK) {
        return (JNIEnv*) env;
    } else {
        return 0;
    }
}

@implementation jmeAppDelegate

@synthesize window = _window;
@synthesize vm = _vm;
@synthesize app = _app;
@synthesize harness = _harness;
@synthesize pauseMethod = _pauseMethod;
@synthesize reactivateMethod = _reactivateMethod;
@synthesize closeMethod = _closeMethod;
@synthesize ctx = _ctx;
@synthesize glview = _glview;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    /**
     * GLES Context initialization
     **/
    _ctx = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];
    _glview.context = self.ctx;
    
    /**
     * Java initilization.
     * Note that though it looks like a JVM is created, in fact only the JNI api is being used here,
     * the whole application and java classpath has been precompiled.
     **/
    JavaVMInitArgs vmArgs;
    vmArgs.version = JNI_VERSION_1_4;
    vmArgs.nOptions = 3;
    vmArgs.ignoreUnrecognized = JNI_TRUE;
    
    JavaVMOption options[vmArgs.nOptions];
    vmArgs.options = options;
    
    options[0].optionString = (char*) "-Davian.bootimage=bootimageBin";
    options[1].optionString = (char*) "-Davian.codeimage=codeimageBin";
    options[2].optionString = (char*) "-Xbootclasspath:[bootJar]:[resourcesJar]";
    
    JavaVM* vm;
    void* env;
    JNI_CreateJavaVM(&vm, &env, &vmArgs);
    JNIEnv* e = (JNIEnv*) env;
    
    /**
     * jME Application initilization.
     **/
    jclass mainClass = (*e)->FindClass(e, "mygame.Main");
    if (! (*e)->ExceptionCheck(e)) {
        jmethodID mainConstructor = (*e)->GetMethodID(e, mainClass, "<init>", "()V");
        if (! (*e)->ExceptionCheck(e)) {
            jobject mainObject = (*e)->NewObject(e, mainClass, mainConstructor);
            if (! (*e)->ExceptionCheck(e)) {
                self.app = mainObject;
                (*e)->NewGlobalRef(e, mainObject);
            }else{
                NSLog(@"Could not create new Application object");
                (*e)->ExceptionDescribe(e);
                (*e)->ExceptionClear(e);
                return NO;
            }
        }else{
            NSLog(@"Could not find Application constructor");
            (*e)->ExceptionDescribe(e);
            (*e)->ExceptionClear(e);
            return NO;
        }
    }else{
        NSLog(@"Could not find Application main class");
        (*e)->ExceptionDescribe(e);
        (*e)->ExceptionClear(e);
        return NO;
    }
    
    /**
     * iOS Harness initilization.
     * The harness is being called after creating the application.
     **/
    jclass harnessClass = (*e)->FindClass(e, "JmeAppHarness");
    if (! (*e)->ExceptionCheck(e)) {
        jmethodID harnessConstructor = (*e)->GetMethodID(e, harnessClass, "<init>", "(Lcom/jme3/app/Application;)V");
        if (! (*e)->ExceptionCheck(e)) {
            jobject harnessObject = (*e)->NewObject(e, harnessClass, harnessConstructor, self.app);
            if (! (*e)->ExceptionCheck(e)) {
                self.harness = harnessObject;
                (*e)->NewGlobalRef(e, harnessObject);
                self.pauseMethod = (*e)->GetMethodID(e, harnessClass, "appPaused", "()V");
                (*e)->ExceptionCheck(e);
                self.reactivateMethod = (*e)->GetMethodID(e, harnessClass, "appReactivated", "()V");
                (*e)->ExceptionCheck(e);
                self.closeMethod = (*e)->GetMethodID(e, harnessClass, "appClosed", "()V");
                (*e)->ExceptionCheck(e);
            }else{
                NSLog(@"Could not create new iOS Harness object");
                (*e)->ExceptionDescribe(e);
                (*e)->ExceptionClear(e);
                return NO;
            }
        }else{
            NSLog(@"Could not find iOS Harness constructor");
            (*e)->ExceptionDescribe(e);
            (*e)->ExceptionClear(e);
            return NO;
        }
    }else{
        (*e)->ExceptionDescribe(e);
        (*e)->ExceptionClear(e);
        NSLog(@"Could not find iOS Harness class");
        return NO;
    }

    self.vm = vm;
    
    //return (*e)->ExceptionCheck(e) ? NO : YES;
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
    JNIEnv* e = getEnv(self.vm);
    if (e) {
        (*e)->CallVoidMethod(e, self.harness, self.pauseMethod);
    }
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
    JNIEnv* e = getEnv(self.vm);
    if (e) {
        (*e)->CallVoidMethod(e, self.harness, self.reactivateMethod);
    }
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    JNIEnv* e = getEnv(self.vm);
    if (e) {
        (*e)->CallVoidMethod(e, self.harness, self.closeMethod);
    }
}

- (void)dealloc
{
    JNIEnv* e = getEnv(self.vm);
    if (e) {
        if(self.app!=nil){
            (*e)->DeleteGlobalRef(e, self.app);
        }
        if(self.harness!=nil){
            (*e)->DeleteGlobalRef(e, self.harness);
        }
    }
    (*self.vm)->DestroyJavaVM(self.vm);
    [_window release];
    _glview.context = nil;
    [_glview release];
    [_ctx release];
    [super dealloc];
}

@end
