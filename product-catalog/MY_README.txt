http://localhost:8081/
RESPONSE
{
  "_links" : {
    "product" : {
      "href" : "http://localhost:8081/product{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8081/profile"
    }
  }
}

http://localhost:8081/product

{
  "_embedded" : {
    "product" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8081/product"
    },
    "profile" : {
      "href" : "http://localhost:8081/profile/product"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 0,
    "totalPages" : 0,
    "number" : 0
  }
}