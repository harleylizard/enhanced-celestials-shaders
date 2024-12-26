package com.harleylizard.enhancedcelestials.shaders;

import java.util.*;

public final class Source {
    public static final String VERSION_MACRO = "#version";

    private final StringBuilder builder = new StringBuilder();
    private final Formatter formatter = new Formatter(builder);

    private final List<String> lines;

    private Source(List<String> lines) {
        this.lines = lines;
    }

    public void add(int i, String line) {
        lines.add(i, line);
    }

    public void uniform(String type, String name) {
        clean();
        lines.add(1, formatter.format("uniform %s %s;", type, name).toString());
    }

    public void constant(String type, String name, String assignment) {
        clean();
        lines.add(1, formatter.format("const %s %s = %s;", type, name, assignment).toString());
    }

    public void result(String name, String replacement) {
        clean();
        var regex = formatter.format("%s = ", name).toString();
        var j = -1;
        for (var i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(regex)) {
                j = i;
                break;
            }
        }
        if (j > -1) {
            clean();
            lines.set(j, formatter.format("%s = %s;", name, replacement).toString());
        }
    }

    public String assignment(int i) {
        var line = lines.get(i);
        return line.substring(line.indexOf("=") + 1, line.lastIndexOf(";")).trim();
    }

    public int version() {
        var line = lines.getFirst();
        var i = line.indexOf(VERSION_MACRO) + VERSION_MACRO.length() + 1;

        return toInt(line.substring(i, line.indexOf(" ", i)));
    }

    public List<Field> outputs() {
        return collectOutputs(lines);
    }

    public int indexOf(String word, Object... arguments) {
        clean();
        var regex = formatter.format(word, arguments).toString();
        for (var i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(regex)) {
                return i;
            }
        }
        return -1;
    }

    private void clean() {
        builder.setLength(0);
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var line : lines) {
            result.append(line).append("\n");
        }
        return result.toString();
    }

    public static Source of(String source) {
        return new Source(new ArrayList<>(Arrays.asList(source.split("\n"))));
    }

    private static int toInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static List<Field> collectOutputs(List<String> lines) {
        var list = new ArrayList<Field>();
        for (var line : lines) {
            if (line.contains("out") && !line.contains(",") && !line.contains("void")) {
                var m = 0;
                var k = 0;
                for (var j = line.length() - 1; j > 0; j--) {
                    if (line.charAt(j) == ' ') {
                        if (m == 1) {
                            k = j;
                            break;
                        }
                        m++;
                    }
                }
                var split = line.substring(k + 1, line.indexOf(";")).split(" ", 2);
                list.add(new Field(split[0], split[1]));
            }
        }
        return Collections.unmodifiableList(list);
    }

    public record Field(String type, String name) {
    }
}
