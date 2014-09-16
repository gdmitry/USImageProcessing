function [V,M,Afafter]=processImage(path)
% ��������������� ���������
A=imread(path);
A=rgb2gray(A);
A=ordfilt2(A, 5, ones(3, 3)); % ��������� ����������
%% ��������� �������������� �����
Af=fft2(A);
initAf=Af;
Af(1,1)=0;
Af=abs(fftshift(Af));
Af=(Af./(max(max(Af))))*255;
% ���������� �� ������� �������
% �������� �������������� �����
Y = ifft2(initAf.*(Af<0.5));
L=Y;
s=size(L);
w=s(2);
h=s(1);
Signal=reshape(L,1,w*h);
Fd=128;% ������� ������������� (��)
FftL=1024;% ���������� ����� ����� �������
%% ������������ ������������� �������
FftS=abs(fft(Signal,FftL));% ��������� �������������� ����� �������
FftS=2*FftS./FftL;% ���������� ������� �� ���������
FftS(1)=FftS(1)/2;% ���������� ���������� ������������ � �������
F=0:Fd/FftL:Fd/2-1/FftL;% ������ ������ ������������ ������� �����
Afafter=FftS(1:240);% ���������� ������� ����� �������
% �� ����� �������� ��������� �� 30 �� 
V=var(FftS(2:240));
M=FftS(1);
end