#this is to inspect codegeneration templates


docker run --rm -v ${PWD}:/local openapitools/openapi-generator-cli author template \
  -g spring \
  -o /local/temp/templates/spring
