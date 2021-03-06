package com.redhat.ceylon.tools.bashcompletion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.ceylon.common.tool.ArgumentParserFactory;
import com.redhat.ceylon.common.tool.Tool;
import com.redhat.ceylon.common.tool.ToolException;
import com.redhat.ceylon.common.tool.ToolFactory;
import com.redhat.ceylon.common.tool.ToolLoader;
import com.redhat.ceylon.common.tool.ToolModel;
import com.redhat.ceylon.tools.bashcompletion.CeylonBashCompletionTool;
import com.redhat.ceylon.tools.CeylonToolLoader;

public class BashCompletionToolTest {
    protected final ArgumentParserFactory apf = new ArgumentParserFactory();
    protected final ToolFactory pluginFactory = new ToolFactory(apf);
    protected final ToolLoader pluginLoader = new CeylonToolLoader(apf, null) {
        protected Enumeration<URL> getServiceMeta() {
            Enumeration<URL> resources;
            try {
                resources = loader.getResources(CeylonExampleTool.class.getName().replace(".", "/")+".properties");
            } catch (IOException e) {
                throw new ToolException(e);
            }
            return resources;
        }
    };
    private PrintStream savedOut;
    private ByteArrayOutputStream out;
    
    Iterable<String> args(String... args) {
        return Arrays.asList(args);
    }
    
    
    public void redirectStdout() {
        this.savedOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }
    
    public void restoreStdout() {
        System.setOut(this.savedOut);
    }
    
    @Test
    public void testPlumbing()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertTrue(model.isPlumbing());
    }
    
    @Test
    public void testToolNameCompletion()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=1",
                        "--",
                        "/path/to/ceylon",
                        ""));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        Assert.assertEquals(
                "example \n" +
        		"", new String(out.toByteArray()));
    }
    
    @Test
    public void testToolNameCompletion_partial()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=1",
                        "--",
                        "/path/to/ceylon",
                        "e"));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        Assert.assertEquals("example \n", new String(out.toByteArray()));
    }
    
    @Test
    public void testOptionNameCompletion()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--"));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        Assert.assertEquals(
                "--file\\=\n" +
                "--list-option\\=\n"+
                "--long-name\n"+
                "--pure-option\n"+
                "--short-name\\=\n"+
                "--thread-state\\=\n"+
                "", new String(out.toByteArray()));
    }
    
    @Test
    public void testOptionNameCompletion_partial()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--l"));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        Assert.assertEquals(
                "--list-option\\=\n" +
                "--long-name\n" +
                "", new String(out.toByteArray()));
    }
    
    @Test
    public void testFileCompletion()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--file="));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        String files = new String(out.toByteArray());
        Assert.assertTrue(files, files.contains("--file=src/\n"));
        Assert.assertTrue(files, files.contains("--file=test/\n"));
    }
    
    @Test
    public void testFileCompletion_partial()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--file=s"));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        String files = new String(out.toByteArray());
        Assert.assertTrue(files, files.contains("--file=src/\n"));
        Assert.assertFalse(files, files.contains("--file=test/ \n"));
    }
    
    @Test
    public void testEnumCompletion()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--thread-state="));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        String files = new String(out.toByteArray());
        Assert.assertTrue(files, files.contains("--thread-state=NEW\n"));
        Assert.assertTrue(files, files.contains("--thread-state=BLOCKED\n"));
        Assert.assertTrue(files, files.contains("--thread-state=RUNNABLE\n"));
    }
    
    @Test
    public void testEnumCompletion_partial()  throws Exception {
        ToolModel<CeylonBashCompletionTool> model = pluginLoader.loadToolModel("bash-completion");
        Assert.assertNotNull(model);
        CeylonBashCompletionTool tool = pluginFactory.bindArguments(model, 
                args("--cword=2",
                        "--",
                        "/path/to/ceylon",
                        "example",
                        "--thread-state=N"));
        try {
            redirectStdout();
            tool.run();
        } finally {
           restoreStdout();
        } 
        String files = new String(out.toByteArray());
        Assert.assertTrue(files, files.contains("--thread-state=NEW \n"));
        Assert.assertFalse(files, files.contains("--thread-state=BLOCKED \n"));
        Assert.assertFalse(files, files.contains("--thread-state=RUNNABLE \n"));
    }

}
