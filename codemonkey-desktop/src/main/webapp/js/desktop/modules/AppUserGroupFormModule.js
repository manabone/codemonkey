
Ext.define('AM.modules.AppUserGroupFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appUserGroupFormModule',
    
    hidden : true,
    
    winTitle : 'AppUserGroup',
    
    modelName : 'AppUserGroup',
    
    modelFields : ['id','name','description','version','creationDate','createdBy','modificationDate','modifiedBy'],
    
    formItems : [

Expected collection or sequence. fieldsJson evaluated instead to freemarker.template.SimpleScalar on line 16, column 24 in ${EntityName}FormModule.js.ftl.
The problematic instruction:
----------
==> list fieldsJson as f [on line 16, column 17 in ${EntityName}FormModule.js.ftl]
----------

Java backtrace for programmers:
----------
freemarker.template.TemplateException: Expected collection or sequence. fieldsJson evaluated instead to freemarker.template.SimpleScalar on line 16, column 24 in ${EntityName}FormModule.js.ftl.
	at freemarker.core.TemplateObject.invalidTypeException(TemplateObject.java:136)
	at freemarker.core.IteratorBlock$Context.runLoop(IteratorBlock.java:190)
	at freemarker.core.Environment.visit(Environment.java:428)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:102)
	at freemarker.core.Environment.visit(Environment.java:221)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:221)
	at freemarker.core.Environment.process(Environment.java:199)
	at freemarker.template.Template.process(Template.java:237)
	at com.codemonkey.FreeMakerGenerator.export(FreeMakerGenerator.java:54)
	at com.codemonkey.FreeMakerGenerator.export(FreeMakerGenerator.java:28)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:49)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:56)
	at com.codemonkey.generator.FileExporter.run(FileExporter.java:32)
	at com.codemonkey.mojo.GenMojo.execute(GenMojo.java:97)
	at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:101)
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:209)
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:153)
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:145)
	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:84)
	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:59)
	at org.apache.maven.lifecycle.internal.LifecycleStarter.singleThreadedBuild(LifecycleStarter.java:183)
	at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:161)
	at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:319)
	at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:156)
	at org.apache.maven.cli.MavenCli.execute(MavenCli.java:537)
	at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:196)
	at org.apache.maven.cli.MavenCli.main(MavenCli.java:141)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:290)
	at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:230)
	at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:409)
	at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:352)
