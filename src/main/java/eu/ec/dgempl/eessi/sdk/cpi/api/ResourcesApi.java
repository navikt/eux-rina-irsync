package eu.ec.dgempl.eessi.sdk.cpi.api;

import eu.ec.dgempl.eessi.sdk.cpi.ApiClient;
import eu.ec.dgempl.eessi.sdk.cpi.ApiClient.CollectionFormat;
import eu.ec.dgempl.eessi.sdk.cpi.model.DiskFileInfoDto;
import eu.ec.dgempl.eessi.sdk.cpi.model.ResourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component ("eu.ec.dgempl.eessi.sdk.cpi.api.ResourcesApi")
public class ResourcesApi {
  private ApiClient apiClient;
  
  public ResourcesApi() {
    this(new ApiClient());
  }
  
  @Autowired
  public ResourcesApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }
  
  public ApiClient getApiClient() {
    return this.apiClient;
  }
  
  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }
  
  public List<ResourceDto> getResources(String resourceLocation, Boolean hardRefresh, List<String> resourceIds) throws RestClientException {
    Object postBody = null;
    if (resourceLocation == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceLocation' when calling getResources");
    } else if (hardRefresh == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'hardRefresh' when calling getResources");
    } else {
      String path = UriComponentsBuilder.fromPath("/Resources").build().toUriString();
      MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
      HttpHeaders headerParams = new HttpHeaders();
      MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
      queryParams.putAll(this.apiClient.parameterToMultiValueMap((CollectionFormat) null, "resourceLocation", resourceLocation));
      queryParams.putAll(this.apiClient.parameterToMultiValueMap(CollectionFormat.valueOf("multi".toUpperCase()), "resourceIds", resourceIds));
      queryParams.putAll(this.apiClient.parameterToMultiValueMap((CollectionFormat) null, "hardRefresh", hardRefresh));
      String[] accepts = new String[]{"application/json"};
      List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
      String[] contentTypes = new String[0];
      MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);
      String[] authNames = new String[0];
      ParameterizedTypeReference<List<ResourceDto>> returnType = new ParameterizedTypeReference<List<ResourceDto>>() {
      };
      return this.apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
  }
  
  public void updateDiskResource(String resourceId, DiskFileInfoDto body) throws RestClientException {
    if (resourceId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceId' when calling updateDiskResource");
    } else if (body == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateDiskResource");
    } else {
      Map<String, Object> uriVariables = new HashMap();
      uriVariables.put("resourceId", resourceId);
      String path = UriComponentsBuilder.fromPath("/DiskResources/{resourceId}").buildAndExpand(uriVariables).toUriString();
      MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
      HttpHeaders headerParams = new HttpHeaders();
      MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
      String[] accepts = new String[]{"application/json"};
      List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
      String[] contentTypes = new String[0];
      MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);
      String[] authNames = new String[0];
      ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
      };
      this.apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, body, headerParams, formParams, accept, contentType, authNames, returnType);
    }
  }
  
  public void updateResource(String resourceId, String resourceType, String resourceVersion) throws RestClientException {
    Object postBody = null;
    if (resourceId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceId' when calling updateResource");
    } else if (resourceType == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceType' when calling updateResource");
    } else if (resourceVersion == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceVersion' when calling updateResource");
    } else {
      Map<String, Object> uriVariables = new HashMap();
      uriVariables.put("resourceId", resourceId);
      String path = UriComponentsBuilder.fromPath("/Resources/{resourceId}").buildAndExpand(uriVariables).toUriString();
      MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
      HttpHeaders headerParams = new HttpHeaders();
      MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
      queryParams.putAll(this.apiClient.parameterToMultiValueMap((CollectionFormat) null, "resourceType", resourceType));
      queryParams.putAll(this.apiClient.parameterToMultiValueMap((CollectionFormat) null, "resourceVersion", resourceVersion));
      String[] accepts = new String[]{"application/json"};
      List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
      String[] contentTypes = new String[0];
      MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);
      String[] authNames = new String[0];
      ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
      };
      this.apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
  }
  
  public ResourceDto updateResources(Object body) throws RestClientException {
    if (body == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateResources");
    } else {
      String path = UriComponentsBuilder.fromPath("/Resources").build().toUriString();
      MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
      HttpHeaders headerParams = new HttpHeaders();
      MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
      String[] accepts = new String[]{"application/json"};
      List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
      String[] contentTypes = new String[0];
      MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);
      String[] authNames = new String[0];
      ParameterizedTypeReference<ResourceDto> returnType = new ParameterizedTypeReference<ResourceDto>() {
      };
      return (ResourceDto) this.apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, body, headerParams, formParams, accept, contentType, authNames, returnType);
    }
  }
}
