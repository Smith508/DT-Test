package com.smith.ryan.dttest.feature.products.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Represents the root of the xml response provided by the data source.
 * <ads/>
 */
@Xml(name = "ads")
data class ProductRoot (

    @Element
    var products: List<Product>
)