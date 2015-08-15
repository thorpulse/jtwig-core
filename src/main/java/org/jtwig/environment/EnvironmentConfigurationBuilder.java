package org.jtwig.environment;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.environment.and.*;
import org.jtwig.extension.Extension;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfigurationBuilder implements Builder<EnvironmentConfiguration> {
    public static EnvironmentConfigurationBuilder configuration () {
        return new EnvironmentConfigurationBuilder(new DefaultEnvironmentConfiguration());
    }

    private Map<String, Object> parameters = new HashMap<>();
    private ValueConfiguration valueConfiguration;
    private final AndFunctionResolverConfigurationBuilder functionResolverConfiguration;
    private final AndRenderConfigurationBuilder renderConfiguration;
    private final AndJtwigParserConfigurationBuilder jtwigParserConfigurationBuilder;
    private final AndResourceResolverConfigurationBuilder resourceResolverConfigurationBuilder;
    private final AndPropertyResolverConfigurationBuilder propertyResolverConfigurationBuilder;
    private final AndEnumerationListStrategyConfigurationBuilder enumerationListStrategyConfigurationBuilder;
    private final AndValueConfigurationBuilder valueConfigurationBuilder;

    public EnvironmentConfigurationBuilder () {
        valueConfiguration = new DefaultValueConfiguration();
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(this);
        renderConfiguration = new AndRenderConfigurationBuilder(this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(this);
        resourceResolverConfigurationBuilder = new AndResourceResolverConfigurationBuilder(this);
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(this);
        valueConfigurationBuilder = new AndValueConfigurationBuilder(this);
    }
    public EnvironmentConfigurationBuilder (EnvironmentConfiguration prototype) {
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(prototype.getFunctionResolverConfiguration(), this);
        renderConfiguration = new AndRenderConfigurationBuilder(prototype.getRenderConfiguration(), this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(prototype.getJtwigParserConfiguration(), this);
        resourceResolverConfigurationBuilder = new AndResourceResolverConfigurationBuilder(prototype.getResourceResolverConfiguration(), this);
        valueConfiguration = prototype.getValueConfiguration();
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(prototype.getPropertyResolverConfiguration(), this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(prototype.getEnumerationListConfiguration(), this);
        valueConfigurationBuilder = new AndValueConfigurationBuilder(prototype.getValueConfiguration(), this);
    }

    @Override
    public EnvironmentConfiguration build() {
        return new EnvironmentConfiguration(
                valueConfiguration,
                resourceResolverConfigurationBuilder.build(),
                functionResolverConfiguration.build(),
                propertyResolverConfigurationBuilder.build(),
                enumerationListStrategyConfigurationBuilder.build(),
                jtwigParserConfigurationBuilder.build(),
                renderConfiguration.build(),
                parameters
        );
    }

    public AndJtwigParserConfigurationBuilder parser () {
        return jtwigParserConfigurationBuilder;
    }

    public AndFunctionResolverConfigurationBuilder functions () {
        return functionResolverConfiguration;
    }

    public AndRenderConfigurationBuilder render () {
        return renderConfiguration;
    }

    public AndResourceResolverConfigurationBuilder resources() {
        return resourceResolverConfigurationBuilder;
    }

    public AndPropertyResolverConfigurationBuilder propertyResolver() {
        return propertyResolverConfigurationBuilder;
    }

    public AndEnumerationListStrategyConfigurationBuilder listEnumeration() {
        return enumerationListStrategyConfigurationBuilder;
    }

    public AndValueConfigurationBuilder value () {
        return valueConfigurationBuilder;
    }

    public EnvironmentConfigurationBuilder withValueConfiguration (ValueConfiguration configuration) {
        this.valueConfiguration = configuration;
        return this;
    }

    public <T> EnvironmentConfigurationBuilder withParameter (String name, T value) {
        this.parameters.put(name, value);
        return this;
    }

    public <T extends Extension> EnvironmentConfigurationBuilder withExtension(T extension) {
        extension.configure(this);
        return this;
    }
}
