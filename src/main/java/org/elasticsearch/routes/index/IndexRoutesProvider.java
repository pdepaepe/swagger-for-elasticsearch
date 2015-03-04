package org.elasticsearch.routes.index;

import net.itimothy.rest.description.Info;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.CompositeRoutesProvider;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.ArrayList;
import java.util.List;

public class IndexRoutesProvider extends CompositeRoutesProvider {
    private final List<RoutesProvider> routeProviders;
    private final String indexOrAlias;

    public IndexRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Index specific routes", client, modelsCatalog);

        this.indexOrAlias = indexOrAlias;

        this.routeProviders = new ArrayList<>();
        this.routeProviders.add(new SearchRoutes(client, getModelsCatalog(), indexOrAlias));
        this.routeProviders.add(new MappingRoutes(client, getModelsCatalog(), indexOrAlias));
        this.routeProviders.add(new DocumentRoutes(client, getModelsCatalog(), indexOrAlias));
    }

    @Override
    protected List<RoutesProvider> getRoutesProviders() {
        return routeProviders;
    }

    @Override
    public Info getInfo() {
        Info info = super.getInfo();
        info.setTitle(info.getTitle() + " for index/alias " + indexOrAlias);
        return info;
    }
}