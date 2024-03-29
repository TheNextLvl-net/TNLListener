package net.nonswag.tnl.listener.api.mapper;

import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public final class Loader {

    static final File MAPPINGS_FOLDER = new File("plugins/Listener/Mappings");

    private Loader() {
    }

    public static boolean load() throws MappingError {
        FileHelper.create(MAPPINGS_FOLDER);
        File[] files = MAPPINGS_FOLDER.listFiles((file, name) -> name.endsWith(".jar"));
        if (files == null) throw new MappingError("No mappings found in <'" + MAPPINGS_FOLDER.getAbsolutePath() + "'>");
        for (File file : files) {
            try {
                load(file);
                return true;
            } catch (MappingError e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static void load(File file) throws MappingError {
        try (JarFile jarFile = new JarFile(file)) {
            MappingFile mappingFile = getMappingFile(jarFile);
            try {
                URLClassLoader loader = new URLClassLoader(file.getName(), new URL[]{file.toURI().toURL()}, Loader.class.getClassLoader());
                Class<?> mapping = Class.forName(mappingFile.getMain(), true, loader).asSubclass(Mapping.class);
                Mapping instance = (Mapping) mapping.getConstructor(File.class).newInstance(file);
                if (!instance.getVersion().equals(Listener.getVersion())) {
                    throw new MappingError("<'%s'> Invalid Mapping version: %s".formatted(file.getName(), instance.getVersion()));
                }
                instance.info();
                instance.onLoad();
                Mapping.setInstance(instance);
            } catch (ClassNotFoundException e) {
                throw new MappingError("Cannot find main class '%s'".formatted(mappingFile.getMain()), e);
            } catch (ClassCastException e) {
                throw new MappingError("main class '%s' does not extend Mapping".formatted(mappingFile.getMain()), e);
            } catch (IllegalAccessException e) {
                throw new MappingError("No public constructor", e);
            } catch (InstantiationException e) {
                throw new MappingError("Abnormal plugin type", e);
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new MappingError(e);
        }
    }

    private static MappingFile getMappingFile(JarFile jarFile) throws MappingError {
        try {
            InputStream stream = jarFile.getInputStream(jarFile.getEntry("mapping.json"));
            if (stream != null) return new MappingFile(new String(stream.readAllBytes()));
            else throw new FileNotFoundException("missing <'mapping.json'> in <'" + jarFile.getName() + "'>");
        } catch (Exception e) {
            throw new MappingError(e);
        }
    }
}
