package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-11T17:20:15+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Microsoft)"
)
@Component
public class AssetMapperImpl implements AssetMapper {

    @Override
    public Asset toAsset(AssetCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Asset.AssetBuilder asset = Asset.builder();

        asset.assetNo( request.getAssetNo() );
        asset.name( request.getName() );
        asset.category( request.getCategory() );
        asset.serialNo( request.getSerialNo() );
        asset.purchasedAt( request.getPurchasedAt() );
        asset.memo( request.getMemo() );

        return asset.build();
    }
}
